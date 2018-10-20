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

import eu.chargetime.simulator.commands.*;
import eu.chargetime.simulator.hardware.*;
import eu.chargetime.simulator.software.ocpp.CoreEventHandler;
import eu.chargetime.simulator.software.ocpp.OCPPClient;

public class ChargeBox {

    private ILock lock;
    private IOutlet outlet;

    public final LockCommand lockCommand;
    public final UnlockCommand unlockCommand;
    public final StatusCommand isLockedCommand;
    public final PluginCommand pluginCommand;
    public final PullPlugCommand pullPluginCommand;

    public ChargeBox() {
        ChargeBoxFirmware firmware = new ChargeBoxFirmware();
        lock = new SimpleLock(firmware,true);
        outlet = new OutletLockDecorator(new SimpleOutlet(firmware), lock);

        lockCommand = new LockCommand(lock);
        unlockCommand = new UnlockCommand(lock);
        isLockedCommand = new StatusCommand(lock, outlet);
        pluginCommand = new PluginCommand(outlet);
        pullPluginCommand = new PullPlugCommand(outlet);

        new OCPPClient("http://localhost:8890", new CoreEventHandler(unlockCommand));
    }
}
