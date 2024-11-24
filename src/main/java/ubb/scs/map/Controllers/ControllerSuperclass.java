package ubb.scs.map.Controllers;

import ubb.scs.map.Facades.ServiceFacade;


public class ControllerSuperclass implements Controller{
    protected final ServiceFacade service = ServiceFacade.getInstance();

    public ControllerSuperclass() {
    }

    public void init() {

    }

}
