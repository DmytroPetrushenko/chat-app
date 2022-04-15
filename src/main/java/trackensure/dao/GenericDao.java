package trackensure.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> {
    T create(T element);

    Optional<T> get(Long id);

    T update(T element);

    boolean delete(T element);

    List<T> getAll();
}
