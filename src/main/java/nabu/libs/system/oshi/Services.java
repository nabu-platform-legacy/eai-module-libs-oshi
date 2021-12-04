package nabu.libs.system.oshi;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebResult;
import javax.jws.WebService;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OperatingSystem;

@WebService
public class Services {
	
	private static SystemInfo systemInfo;
	private static HardwareAbstractionLayer hardware;
	
	private static SystemInfo getSystemInfo() {
		if (systemInfo == null) {
			systemInfo = new SystemInfo();
		}
		return systemInfo;
	}
	
	private static HardwareAbstractionLayer getHardware() {
		if (hardware == null) {
			hardware = getSystemInfo().getHardware();
		}
		return hardware;
	}
	
	@WebResult(name = "memory")
	public GlobalMemory memory() {
		return getHardware().getMemory();
	}
	
	@WebResult(name = "processor")
	public CentralProcessor processor() {
		return getHardware().getProcessor();
	}
	
	@WebResult(name = "os")
	public OperatingSystem os() {
		return getSystemInfo().getOperatingSystem();
	}
	
	@WebResult(name = "disks")
	public List<HWDiskStore> disks() {
		return getHardware().getDiskStores();
	}
	
	@WebResult(name = "load")
	public List<Double> load() {
		double[] systemLoadAverage = processor().getSystemLoadAverage(3);
		List<Double> load = new ArrayList<Double>();
		for (double single : systemLoadAverage) {
			load.add(single);
		}
		return load;
	}
	
	@WebResult(name = "fileSystem")
	public FileSystem fileSystem() {
		return getSystemInfo().getOperatingSystem().getFileSystem();
	}
}
