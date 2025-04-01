package server.commands;

import common.network.requests.*;
import common.network.responses.*;
import server.repositories.ProductRepository;

/**
 * Команда 'update'. Обновляет элемент коллекции.
 */
public class Update extends Command {
  private final ProductRepository productRepository;

  public Update(ProductRepository productRepository) {
    super("update ", "update collection element value by ID");
    this.productRepository = productRepository;
  }

  /**
   * Выполняет команду
   * @return Успешность выполнения команды.
   */
  @Override
  public Response apply(Request request) {
    var req = (UpdateRequest) request;
    try {
      if (!productRepository.checkExist(req.id)) {
        return new UpdateResponse( "There is no group with this ID in the collection!");
      }
      if (!req.updatedProduct.validate()) {
        return new UpdateResponse( "Group fields are not valid! Group not updated!");
      }

      productRepository.getById(req.id).update(req.updatedProduct);
      return new UpdateResponse(null);
    } catch (Exception e) {
      return new UpdateResponse(e.toString());
    }
  }
}
