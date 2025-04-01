package server.commands;

import common.network.requests.Request;
import common.network.responses.*;
import server.repositories.ProductRepository;

/**
 * Команда 'show'. Выводит все элементы коллекции.

 */
public class Show extends Command {
  private final ProductRepository productRepository;

  public Show(ProductRepository productRepository) {
    super("show", "display all elements of a collection");
    this.productRepository = productRepository;
  }

  /**
   * Выполняет команду
   * @return Успешность выполнения команды.
   */
  @Override
  public Response apply(Request request) {
    try {
      return new ShowResponse(productRepository.sorted(), null);
    } catch (Exception e) {
      return new ShowResponse(null, e.toString());
    }
  }
}
