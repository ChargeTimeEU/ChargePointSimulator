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

import eu.chargetime.simulator.hardware.io.events.LockedHWEvent;
import eu.chargetime.simulator.hardware.io.events.UnlockedHWEvent;

public class SimpleLock implements ILock {
    private final IBus bus;
    boolean locked;

    public SimpleLock(IBus bus, boolean initialState) {
        this.bus = bus;
        locked = initialState;
    }

    @Override
    public void lock() {
        if (!locked) {
            locked = true;
            bus.interrupt(new LockedHWEvent());
        }
    }

    @Override
    public void unlock() {
        if (locked) {
            locked=false;
            bus.interrupt(new UnlockedHWEvent());
        }
    }

    @Override
    public boolean isLocked() {
        return locked;
    }
}
