package client.commands;

import client.forms.ProductForm;
import client.network.UDPClient;
import client.utility.console.Console;
import common.exceptions.*;
import common.network.requests.*;
import common.network.responses.*;

import java.io.IOException;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.

 */
public class Add extends Command {
  private final Console console;
  private final UDPClient client;

  public Add(Console console, UDPClient client) {
    super("add {element}");
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
      if (!arguments[1].isEmpty()) throw new WrongAmountOfElementsException();
      console.println("* Creating a new band:");

      var newProduct = (new ProductForm(console)).build();
      var response = (AddResponse) client.sendAndReceiveCommand(new AddRequest(newProduct));
      if (response.getError() != null && !response.getError().isEmpty()) {
        throw new APIException(response.getError());
      }

      console.println("New band with id=" + response.newId + " added successfully!");
      return true;

    } catch (WrongAmountOfElementsException exception) {
      console.printError("Wrong amount of arguments!");
      console.println("Using: '" + getName() + "'");
    } catch (InvalidFormException exception) {
      console.printError("Band fields are invalid! Band is not created!");
    } catch(IOException e) {
      console.printError("Server interaction error");
    } catch (APIException e) {
      console.printError(e.getMessage());
    } catch (IncorrectInputInScriptException ignored) {}
    return false;
  }
}
