package server.commands;

import common.domain.Product;
import common.network.requests.*;
import common.network.responses.*;
import server.repositories.ProductRepository;

/**

 */
public class AddIfMax extends Command {
  private final ProductRepository productRepository;

  public AddIfMax(ProductRepository productRepository) {
    super("add_if_max ",  "add a new item to a collection if its rating exceeds the maximum rating of this collection");
    this.productRepository = productRepository;
  }

  /**
   * Выполняет команду
   * @return Успешность выполнения команды.
   */
  @Override
  public Response apply(Request request) {
    try {
      var req = (AddIfMaxRequest) request;
      var maxPrice = maxPrice();
      if (req.product.getPrice() > maxPrice) {
        var newId = productRepository.add(req.product);
        return new AddIfMaxResponse(true, newId, null);
      }
      return new AddIfMaxResponse(false, -1, null);
    } catch (Exception e) {
      return new AddIfMaxResponse(false, -1, e.toString());
    }
  }

  private Long maxPrice() {
    return productRepository.get().stream()
      .map(Product::getPrice)
      .mapToLong(Long::longValue)
      .max()
      .orElse(-1);
  }
}
