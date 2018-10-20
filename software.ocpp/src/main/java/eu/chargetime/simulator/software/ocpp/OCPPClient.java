package eu.chargetime.simulator.software.ocpp;
/*
    ChargeTime.eu - Charge Point Simulator
    
    MIT License

    Copyright (C) 2016 Thomas Volden <tv@chargetime.eu>

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
 */

import eu.chargetime.ocpp.ClientEvents;
import eu.chargetime.ocpp.JSONClient;
import eu.chargetime.ocpp.OccurenceConstraintException;
import eu.chargetime.ocpp.UnsupportedFeatureException;
import eu.chargetime.ocpp.feature.profile.ClientCoreProfile;
import eu.chargetime.ocpp.model.Confirmation;

import java.util.concurrent.CompletionStage;

public class OCPPClient {

    public OCPPClient(String uri, CoreEventHandler handler) {

        ClientCoreProfile coreProfile = new ClientCoreProfile(handler);
        JSONClient client = new JSONClient(coreProfile, "OCPPSimulator");

        client.connect(uri, new ClientEvents() {
            @Override
            public void connectionOpened() {
                System.out.println("Connected!");
                try {
                    CompletionStage<Confirmation> confirmation = client.send(coreProfile.createBootNotificationRequest("ChargeTimeEU", "Simulator"));
                    confirmation.whenComplete((confirmation1, throwable) -> System.out.println("Booted"));
                } catch (UnsupportedFeatureException e) {
                    e.printStackTrace();
                } catch (OccurenceConstraintException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void connectionClosed() {
                System.out.println("Connection lost!");
            }
        });
    }
}
