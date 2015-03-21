package at.rosinen.Noctis.events;

/**
 * Created by Harald on 21.03.2015.

**/



public class NoctisEventsQueryEvent {
    private NoctisQueryEnum queryEnum;
    public NoctisEventsQueryEvent(NoctisQueryEnum queryEnum) {
        this.queryEnum = queryEnum;
    }

    public NoctisQueryEnum getQueryEnum() {
        return this.queryEnum;
    }
}