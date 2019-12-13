package algo;

/**
 * Interface pour les iterateurs
 *
 */
public interface Iterator<E> {
	public boolean hasNext();
	public E next();
	public void remove(E... args);
}
