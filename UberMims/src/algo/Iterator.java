package algo;

public interface Iterator<E> {
	public boolean hasNext();
	public Iterator<E> next();
	public void remove(E... args);
}
