package at.rosinen.Noctis.Service;

import at.rosinen.Noctis.Model.NoctisEvent;

import java.util.List;

/**
 * Created by Harald on 20.03.2015.
 */
public interface INoctisEventObtainer {

    List<NoctisEvent> obtainNoctisEvents();
}
