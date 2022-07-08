package be.dgcity.market.Exceptions;

public class EntityAlreadyExistsException extends RuntimeException {

    private final Object id;
    private final Class<?> clazz;

    public EntityAlreadyExistsException(Object id, Class<?> clazz) {
        super("Element of type '"+ clazz.getSimpleName() +"' already exists with id {" + id + "}");
        this.id = id;
        this.clazz = clazz;
    }

    public EntityAlreadyExistsException(String message, Object id, Class<?> clazz) {
        super(message);
        this.id = id;
        this.clazz = clazz;
    }
}
