package client.commands;

import client.utility.console.Console;

/**
 * Команда 'exit'. Завершает выполнение.
 * @author maxbarsukov
 */
public class Exit extends Command {
  private final Console console;

  public Exit(Console console) {
    super("exit");
    this.console = console;
  }

  /**
   * Выполняет команду
   * @return Успешность выполнения команды.
   */
  @Override
  public boolean apply(String[] arguments) {
    if (!arguments[1].isEmpty()) {
      console.println("Using: '" + getName() + "'");
      return false;
    }

    console.println("Completion of execution...");

    return true;
  }
}
