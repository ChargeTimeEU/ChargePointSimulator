package eu.chargetime.simulator;
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

import eu.chargetime.simulator.commands.IsLockedCommand;
import eu.chargetime.simulator.commands.LockCommand;
import eu.chargetime.simulator.commands.UnlockCommand;
import eu.chargetime.simulator.hardware.Events.LockEvents;
import eu.chargetime.simulator.hardware.ILock;
import eu.chargetime.simulator.hardware.SimpleLock;

public class LockBox {

    private ILock lock;

    public final LockCommand lockCommand;
    public final UnlockCommand unlockCommand;
    public final IsLockedCommand isLockedCommand;

    public LockBox() {
        LockEvents firmware = new LockBoxFirmware();
        lock = new SimpleLock(firmware,true);
        lockCommand = new LockCommand(lock);
        unlockCommand = new UnlockCommand(lock);
        isLockedCommand = new IsLockedCommand(lock);
    }
}
