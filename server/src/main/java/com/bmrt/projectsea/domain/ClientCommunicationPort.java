package com.bmrt.projectsea.domain;

public interface ClientCommunicationPort {

    void sendToAllPLayer(Action action, Ship ship);
}
