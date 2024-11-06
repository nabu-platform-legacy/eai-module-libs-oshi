/*
* Copyright (C) 2019 Alexander Verbruggen
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License
* along with this program. If not, see <https://www.gnu.org/licenses/>.
*/

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
