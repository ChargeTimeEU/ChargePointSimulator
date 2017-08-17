package eu.chargetime.simulator.hardware;
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

import eu.chargetime.simulator.hardware.io.IHWEventObserver;
import eu.chargetime.simulator.hardware.io.IObservable;
import eu.chargetime.simulator.hardware.io.events.IHardwareEvent;

import java.util.HashSet;

public class BusController implements IObservable<IHWEventObserver>, IBus {

    private final HashSet<IHWEventObserver> handlerList;

    public BusController() {
        handlerList = new HashSet<>();
    }

    public void subscribe(IHWEventObserver observer) {
        handlerList.add(observer);
    }

    public void unsubscribe(IHWEventObserver observer) {
        handlerList.remove(observer);
    }

    @Override
    public void interrupt(IHardwareEvent event) {
        for (IHWEventObserver handler: handlerList) {
            if (handler != null)
                handler.newEvent(event);
        }
    }
}
