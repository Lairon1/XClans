package com.lairon.xclans.registered;

import com.lairon.xclans.XClans;
import com.lairon.xclans.commandapi.ClanCommand;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class RegisteredCommands {

    private HashMap<Class<ClanCommand>, ClanCommand> registeredCommands = new HashMap<>();
    private XClans main;
    private RegisteredClans registeredClans;

    public RegisteredCommands(XClans main, RegisteredClans registeredClans) {
        this.main = main;
        this.registeredClans = registeredClans;
    }

    public <T extends ClanCommand> void registerCommand(Class<T> commandClass){
        if(getCommandInstance(commandClass) != null)
            throw new IllegalArgumentException("This command is already registered");
        try {
            ClanCommand command = (ClanCommand) commandClass.getConstructors()[0].newInstance(main, registeredClans, this);
            registeredCommands.put((Class<ClanCommand>) commandClass, command);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public <T extends ClanCommand> T getCommandInstance(Class<T> commandClass){
        if(!registeredCommands.containsKey(commandClass)) return null;
        else return (T) registeredCommands.get(commandClass);
    }

    public ClanCommand getCommandInstanceByArgument(String argument){
        var first = registeredCommands
                .values()
                .stream()
                .filter(c -> c.argument().equalsIgnoreCase(argument))
                .findFirst();
        if(first.isEmpty()) return null;
        else return first.get();
    }

}
