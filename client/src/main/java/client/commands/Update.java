package client.commands;

import client.forms.ProductForm;
import client.network.UDPClient;
import client.utility.console.Console;
import common.exceptions.*;
import common.network.requests.UpdateRequest;
import common.network.responses.UpdateResponse;

import java.io.IOException;

/**
 * Команда 'update'. Обновляет элемент коллекции.
 */
public class Update extends Command {
  private final Console console;
  private final UDPClient client;

  public Update(Console console, UDPClient client) {
    super("update ID {element}");
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

      console.println("* Enter updated info for Band:");
      var updatedProduct = (new ProductForm(console)).build();

      var response = (UpdateResponse) client.sendAndReceiveCommand(new UpdateRequest(id, updatedProduct));
      if (response.getError() != null && !response.getError().isEmpty()) {
        throw new APIException(response.getError());
      }

      console.println("Band deleted successfully.");
      return true;

    } catch (WrongAmountOfElementsException exception) {
      console.printError("Wrong amount of arguments!");
      console.println("Using: '" + getName() + "'");
    } catch (InvalidFormException exception) {
      console.printError("Band fields are invalid! Band are not created!");
    } catch (NumberFormatException exception) {
      console.printError("ID must be a number!");
    } catch(IOException e) {
      console.printError("Server interaction error");
    } catch (APIException e) {
      console.printError(e.getMessage());
    } catch (IncorrectInputInScriptException ignored) {}
    return false;
  }
}
