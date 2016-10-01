package frc.team4828.landrovaltoast;

import jaci.openrio.toast.core.command.AbstractCommand;
import jaci.openrio.toast.core.command.IHelpable;
import jaci.openrio.toast.lib.log.Logger;

import java.util.Objects;

class Commands {
    static class MyCommand extends AbstractCommand implements IHelpable {    // IHelpable is optional, but provides a help message
        public String getCommandName() {
            return "logtest";
        }

        Logger logger = new Logger("LogTest", Logger.ATTR_DEFAULT);

        public void invokeCommand(int argLength, String[] args, String fullCommand) {
            if (args.length == 0) logger.info(getHelp());
            else if (Objects.equals(args[0], "severe")) logger.severe("SEVERE");
            else if (Objects.equals(args[0], "info")) logger.info("INFO");
            else if (Objects.equals(args[0], "warn")) logger.warn("WARN");
            else if (Objects.equals(args[0], "error")) logger.error("ERROR");
            else if (Objects.equals(args[0], "raw")) logger.raw("RAW");
            else if (Objects.equals(args[0], "debug")) logger.debug("DEBUG");
        }

        public String getHelp() {
            return "Put type of log as argument to test logging. Argument types: severe, info, warn, error, raw, debug";
        }
    }
}