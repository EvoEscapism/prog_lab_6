package client.commands;

import client.network.UDPClient;
import client.utility.console.Console;
import common.exceptions.*;
import common.network.requests.*;
import common.network.responses.*;

import java.io.IOException;

/**
 * Команда 'remove_by_id'. Удаляет элемент из коллекции.
 */
public class RemoveById extends Command {
  private final Console console;
  private final UDPClient client;

  public RemoveById(Console console, UDPClient client) {
    super("remove_by_id <ID>");
    this.console = console;
    this.client = client;
  }

  /**
   * Выполняет команду
   * @return Успешность выполнения команды.
   */
  @Override
  public boolean apply(String[] arguments) {
    try {
      if (arguments[1].isEmpty()) throw new WrongAmountOfElementsException();
      var id = Integer.parseInt(arguments[1]);

      var response = (RemoveByIdResponse) client.sendAndReceiveCommand(new RemoveByIdRequest(id));
      if (response.getError() != null && !response.getError().isEmpty()) {
        throw new APIException(response.getError());
      }

      console.println("Band deleted successfully");
      return true;
    } catch (WrongAmountOfElementsException exception) {
      console.printError("Wrong amount of arguments!");
      console.println("Using: '" + getName() + "'");
    } catch (NumberFormatException exception) {
      console.printError("ID must be a number!");
    } catch(IOException e) {
      console.printError("Server interaction error");
    } catch (APIException e) {
      console.printError(e.getMessage());
    }
    return false;
  }
}
