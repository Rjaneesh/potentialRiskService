package com.bdp.innovation.potentialRiskService.daoimpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.bdp.innovation.potentialRiskService.common.DateUtils;
import com.bdp.innovation.potentialRiskService.dao.ISchedulerStatusDao;
import com.bdp.innovation.potentialRiskService.model.SchedulerLog;
import com.bdp.innovation.potentialRiskService.model.SchedulerStatus;
import com.bdp.innovation.potentialRiskService.repository.SchedulerLogRepository;
import com.bdp.innovation.potentialRiskService.repository.SchedulerStatusRepository;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SchedulerStatusDaoImpl implements ISchedulerStatusDao {

	@Autowired
	private SchedulerLogRepository logRepository;
	
	@Autowired
	private SchedulerStatusRepository schedulerStatusRepository;

//	@Autowired
//	private IUserManagementServiceDao userManagementServiceDao;

	@Autowired
	private EntityManager entityManager;

	@Value("${rm.schedule.run.status:yes}")
	private String scheduledStatus;

	@Override
	public void checkCurrentDate() {
		Object obj = entityManager.createNativeQuery("select now()").getSingleResult();
		log.info("----------------------------------------Database Time:: " + String.valueOf(obj));
	}

	@Override
	public void saveSchedulor(SchedulerStatus schedulerStatus) {
		schedulerStatusRepository.saveAndFlush(schedulerStatus);
	}

	@Override
	public List<SchedulerStatus> getAllSchedulerStatus() {
		return schedulerStatusRepository.findAll();
	}

	@Override
	public SchedulerStatus findSchedulerStatus(String schedulerName) {
		SchedulerStatus schedulerStatus = null;
		List<SchedulerStatus> list = schedulerStatusRepository.findAllBySchedulerName(schedulerName);
		if (!CollectionUtils.isEmpty(list)) {
			schedulerStatus = list.get(0);
		}
		return schedulerStatus;
	}

	@Override
	public void updateScheduledStatus(String schedulerName, String error) {
		SchedulerStatus schedulerStatus = null;
		List<SchedulerStatus> list = schedulerStatusRepository.findAllBySchedulerName(schedulerName);
		if (!CollectionUtils.isEmpty(list)) {
			schedulerStatus = list.get(0);
			schedulerStatus.setRemarks(error);
			schedulerStatusRepository.saveAndFlush(schedulerStatus);
		}
	}

	@Override
	public Boolean updateScheduledLogs(String scheduledName, final String expression) {
		SchedulerStatus schedulerStatus = null;
		LocalDateTime next = null;
		CronExpression cronTrigger = null;
		Date nextDate = null;
		Boolean flag = false;
		try {
			if (scheduledStatus != null && "YES".equalsIgnoreCase(scheduledStatus)) {
				cronTrigger = CronExpression.parse(expression);
				next = cronTrigger.next(LocalDateTime.now());
				if (next != null)
					nextDate = Date.from(next.atZone(ZoneId.systemDefault()).toInstant());
				Date nowDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
				Long duretion = DateUtils.getMinutesDifference(nowDate, nextDate) + 1;
				if (scheduledName != null) {
					schedulerStatus = findSchedulerStatus(scheduledName);
					if (ObjectUtils.isEmpty(schedulerStatus)) {
						schedulerStatus = SchedulerStatus.builder().duretion(duretion).cronExpression(expression)
								.schedulerName(scheduledName).lastScheduledDate(nowDate).nextScheduledDate(nextDate)
								.schedulerStatus(Boolean.FALSE).build();
					} else {
						schedulerStatus.setCronExpression(expression);
						schedulerStatus.setLastScheduledDate(nowDate);
						schedulerStatus.setNextScheduledDate(nextDate);
						schedulerStatus.setDuretion(duretion);
					}
					schedulerStatus.setRemarks(schedulerStatus.getSchedulerStatus() ? "Running" : "Stoped");
					saveSchedulor(schedulerStatus);
					flag = schedulerStatus.getSchedulerStatus();
				}
			}
		} catch (Exception e) {
			log.error("Error :: SchedulerStatusDaoImpl :: updateScheduledLogs", e.getMessage());
		}
		return flag;
	}

	@Override
	public void saveSchedulerLog(String name, Date startTime, Date endTime) {
		SchedulerLog schLog = null;
		try {
			long diff = DateUtils.getSecondsDifference(startTime, endTime);
			schLog = SchedulerLog.builder().schedulerName(name).startTime(startTime).endTime(endTime).duration(diff)
					.build();
			logRepository.saveAndFlush(schLog);
		} catch (Exception e) {
			log.error("Error:: {}", e.getMessage());
		}
	}

	@Override
	public Integer saveSchedulerLogWithStatus(String schedulerName, Integer sId, Boolean isEnded) {
		SchedulerLog schedulerLog = null;
		try {
			if (!ObjectUtils.isEmpty(schedulerName)) {
				if (!ObjectUtils.isEmpty(isEnded) && isEnded.equals(Boolean.FALSE)) {
					schedulerLog = SchedulerLog.builder().schedulerName(schedulerName).startTime(new Date()).status(0).build();
					schedulerLog = logRepository.saveAndFlush(schedulerLog);
				} else if(!ObjectUtils.isEmpty(isEnded) && isEnded.equals(Boolean.TRUE)) {
					schedulerLog = logRepository.findById(sId).get();
					if(!ObjectUtils.isEmpty(schedulerLog)) {
						schedulerLog.setEndTime(new Date());
						schedulerLog.setStatus(1);
						long difference = DateUtils.getSecondsDifference(schedulerLog.getStartTime(), schedulerLog.getEndTime());
						schedulerLog.setDuration(difference);
						schedulerLog = logRepository.saveAndFlush(schedulerLog);
					}
				}
			}
		} catch (Exception e) {
			log.error("Error:: {}", e.getMessage());
		}
		if (!ObjectUtils.isEmpty(schedulerLog)) {
			return schedulerLog.getId();
		} else {
			return null;
		}
	}

	@Override
	public List<SchedulerLog> saveAllSchedulerLogs(List<SchedulerLog> logList) {
		return logRepository.saveAllAndFlush(logList);
	}


}
