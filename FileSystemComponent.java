
public abstract class FileSystemComponent {
	private String name;
	protected long size;

	public FileSystemComponent(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract boolean isFile();

	public abstract long getSize();

}
