package beer.gpio.device;

import java.io.FileOutputStream;
import java.io.IOException;

import beer.gpio.util.FilePaths;

public abstract class BasePin {

	protected final int pinNumber;
	protected PinState state = PinState.UNKNOWN;
	private FilePaths filePaths = new FilePaths();

	public BasePin(int pinNumber, Direction pinDirection) throws TemperatureSensorException {
		this.pinNumber = pinNumber;
		
		writeFile(getFilePaths().getUnexportPath(), pinNumber + "");
		writeFile(getFilePaths().getExportPath(), pinNumber + "");
		writeFile(getFilePaths().getDirectionPath(pinNumber), pinDirection.getValue());
	}
	
	public FilePaths getFilePaths() {
		return filePaths;
	}

	public int getPinNumber() {
		return pinNumber;
	}

	public PinState getPinState() {
		return state;
	}

	protected void writeFile(String fileName, String value) throws TemperatureSensorException {
		try {
			// TODO: doesn't throw an exception if the file is not there!
			FileOutputStream fos = new FileOutputStream(fileName);
			fos.write(value.getBytes());
			fos.close();
		} catch (IOException ex) {
			throw new TemperatureSensorException("Could not write to GPIO file: ", ex);
		}
	}

	public enum Direction {
		IN("in"), OUT("out"), PWM("pwm");

		private String value;

		private Direction(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	public enum PinState {
		ON, OFF, UNKNOWN;
	}

}