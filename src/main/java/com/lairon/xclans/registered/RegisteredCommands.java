package com.lairon.xclans.registered;

import com.lairon.xclans.api.commandapi.ClanCommand;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;

public class RegisteredCommands {

    private HashMap<Class<ClanCommand>, ClanCommand> registeredCommands = new HashMap<>();

    public <T extends ClanCommand> void registerCommand(@NotNull Class<T> commandClass){
        Validate.notNull(commandClass, "Command class cannot be null");
        if(getCommandInstance(commandClass) != null)
            throw new IllegalArgumentException("This command is already registered");
        try {
            ClanCommand command = (ClanCommand) commandClass.getConstructors()[0].newInstance();
            registeredCommands.put((Class<ClanCommand>) commandClass, command);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public <T extends ClanCommand> T getCommandInstance(@NotNull Class<T> commandClass){
        Validate.notNull(commandClass, "Command class cannot be null");
        if(!registeredCommands.containsKey(commandClass)) return null;
        else return (T) registeredCommands.get(commandClass);
    }

    @Nullable
    public ClanCommand getCommandInstanceByArgument(@NotNull String argument){
        Validate.notNull(argument, "Clan command argument cannot be null");
        Validate.notEmpty(argument, "Clan command argument cannot be empty");
        var first = registeredCommands
                .values()
                .stream()
                .filter(c -> c.argument().equalsIgnoreCase(argument))
                .findFirst();
        if(first.isEmpty()) return null;
        else return first.get();
    }

    public Collection<String> getAllRegisteredCommands(){
        return registeredCommands.values();
    }
}
