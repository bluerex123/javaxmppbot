/*
 *  JavaXMPPBot.modules - official modules for JavaXMPPBot
 *  Copyright 2010 Mikhail Telnov <michael.telnov@gmail.com>
 *
 *  This file is part of JavaXMPPBot.modules.
 *
 *  JavaXMPPBot.modules is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  JavaXMPPBot.modules is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with JavaXMPPBot.modules.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  $Id$
 *
 */

package com.devti.JavaXMPPBot.modules;

import com.devti.JavaXMPPBot.Message;
import com.devti.JavaXMPPBot.Module;
import com.devti.JavaXMPPBot.Bot;
import com.devti.JavaXMPPBot.Command;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Debug extends Module {

    private static final Logger logger = Logger.getLogger(Debug.class.getName());

    public Debug(Bot bot) {
        super(bot);
        try {
            bot.registerCommand(new Command("threads", "list threads of this bot", true, this));
            bot.registerCommand(new Command("runtime", "show runtime information", true, this));
        } catch (Exception e) {
            logger.log(Level.WARNING, "Can't register a command.", e);
        }
    }

    @Override
    public void processCommand(Message msg) {
        // List active threads
        if (msg.command.equals("threads")) {
                Thread[] threads = new Thread[Thread.activeCount()];
                int threadsCount = Thread.enumerate(threads);
                String message = new String();
                for (int i = 0; i < threadsCount; i++) {
                    message += (i + 1) + ") " + threads[i].getName() + " [" + threads[i].getState().toString() + "]\n";
                }
                bot.sendReply(msg, message);
        }
        // Show runtime information
        if (msg.command.equals("runtime")) {
                Runtime runtime = Runtime.getRuntime();
                String message = String.format(
                    "Available processors: %d\nAvailable memory: %,d bytes\nTotal memory: %,d bytes\nMax memory: %,d bytes",
                    runtime.availableProcessors(),
                    runtime.freeMemory(),
                    runtime.totalMemory(),
                    runtime.maxMemory()
                );
                bot.sendReply(msg, message);
        }
    }

}
