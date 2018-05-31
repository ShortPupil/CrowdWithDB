package crowdtag.model.businesslogic;

import crowdtag.hibernate.entity.WorkerEntity;
import crowdtag.hibernate.entity.WorkerPortraitEntity;

public class Worker {
private WorkerEntity workerEntity;
private WorkerPortraitEntity workerportraitEntity;
public void setWorkerEntity(WorkerEntity workerEntity) {
    this.workerEntity = workerEntity;
}
public WorkerEntity getWorkerEntity() {
    return workerEntity;
}

public void setWorkerPortraitEntity (WorkerPortraitEntity  workerportraitEntity) {
    this.workerportraitEntity= workerportraitEntity;
}
public WorkerPortraitEntity getWorkerPortraitEntity() {
    return workerportraitEntity;
}
}
