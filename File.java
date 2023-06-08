
public class File extends FileSystemComponent {

	public File(String name, long size) {
		super(name);
		this.size = size;
	}

	@Override
	public long getSize() {
		return size;
	}

	@Override
	public boolean isFile() {
		return true;
	}

}
