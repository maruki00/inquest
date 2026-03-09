package domain.entity;

import domain.contract.IEvent;

public record Event implements IEvent {
    public Integer ID ;
    public Event(int id){
        this.ID = id;
    }
    public int GetID(){
        return this.ID;
    }

    public String GetLael(){
        return "";
    }

}
