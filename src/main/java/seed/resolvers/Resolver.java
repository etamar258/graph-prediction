package seed.resolvers;

public interface Resolver<ResolvedType> {
    /**
     * Resolve an object into 'ResolvedType'
     */
    ResolvedType resolve(Object data) throws ClassCastException;
}
