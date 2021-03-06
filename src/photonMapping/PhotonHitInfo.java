
package photonMapping;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableUtils;

import photonMapping.shape.IAxisScale;

public class PhotonHitInfo implements Writable, IAxisScale {
	private Color color;

	private Vector position;

	private Vector incidentDirection;

	public Vector getPosition() {
		// TODO;
		return null;
	}

	public PhotonHitInfo(Color color, Vector position, Vector incidentDirection) {
		this.color = color;
		this.position = position;
		this.incidentDirection = incidentDirection;
	}

	public void clear() {
		this.color.clear();
		this.position.clear();
		this.incidentDirection.clear();
	}

	public void fromFormatString(String input) {
		this.clear();
		int findex = input.indexOf('|');
		this.color.fromFormatString(input.substring(1, findex));
		int sindex = input.indexOf('|', findex + 1);
		this.position.fromFormatString(input.substring(findex + 1, sindex));
		this.incidentDirection.fromFormatString(input.substring(sindex + 1, input.length() - 1));
	}

	public String asFormatString() {
		StringBuilder stringToBuild = new StringBuilder();
		stringToBuild.append('[');
		stringToBuild.append(this.color.asFormatString()).append('|').append(this.position.asFormatString())
		.append('|').append(this.incidentDirection.asFormatString());
		stringToBuild.append(']');
		return stringToBuild.toString();
	}

	@Override
	public void readFields(DataInput input) throws IOException {
		int length = WritableUtils.readVInt(input);
		byte[] bytes = new byte[length];
		input.readFully(bytes, 0, length);
		this.fromFormatString(new String(bytes));
	}

	@Override
	public void write(DataOutput output) throws IOException {
		String content = this.asFormatString();
		WritableUtils.writeVInt(output, content.length());
		output.writeBytes(content);
	}

	@Override
	public String toString() {
		return this.asFormatString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see photonMapping.shape.IAxisScale#minX()
	 */
	@Override
	public double minX() {
		return this.position.minX();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see photonMapping.shape.IAxisScale#maxX()
	 */
	@Override
	public double maxX() {
		return this.position.maxX();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see photonMapping.shape.IAxisScale#minY()
	 */
	@Override
	public double minY() {
		return this.position.minY();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see photonMapping.shape.IAxisScale#maxY()
	 */
	@Override
	public double maxY() {
		return this.position.maxY();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see photonMapping.shape.IAxisScale#minZ()
	 */
	@Override
	public double minZ() {
		return this.position.minZ();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see photonMapping.shape.IAxisScale#maxZ()
	 */
	@Override
	public double maxZ() {
		return this.position.maxZ();
	}

}
