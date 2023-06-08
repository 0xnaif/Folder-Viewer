import java.util.ArrayList;

public class Folder extends FileSystemComponent {
	private ArrayList<FileSystemComponent> components = new ArrayList<FileSystemComponent>();
	private String path;

	public Folder(String name, String path) {
		super(name);
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	@Override
	public long getSize() {
		size = 0;
		for (FileSystemComponent c : components)
			size += c.getSize();
		return size;
	}

	public void addComponent(FileSystemComponent component) {
		components.add(component);
	}

	@Override
	public boolean isFile() {
		return false;
	}

	public ArrayList<FileSystemComponent> getComponents() {
		return components;
	}
}
