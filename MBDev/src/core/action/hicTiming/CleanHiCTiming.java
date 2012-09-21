/*******************************************************************************
 * Montpellier Biology Developpment
 * This project aims to create a tool to process and analyse data of a project in the field of Biology.
 * Copyright (C) 2012, Nicolas Fourel
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Authors:
 * 	Nicolas Fourel <nicolas.fourel@live.fr>
 * 	Romain Desprat <rdesprat@gmail.com>
 ******************************************************************************/
package core.action.hicTiming;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import core.action.file.LineFileReader;
import core.fileLine.HicTimingLine;

/**
 * @author Nicolas Fourel
 * @version 0.1
 */
public class CleanHiCTiming extends LineFileReader<HicTimingLine> {

	/** Generated default serial version ID */
	private static final long serialVersionUID = 4495558610088027673L;

	private final File outputFile;		// The output file.

	private boolean isReadyToWrite;		// Says if the output file is ready.
	private FileWriter fw;				// The file writer.
	private BufferedWriter out;			// The buffered writer.


	/**
	 * Constructor of {@link CleanHiCTiming}
	 * @param inputFile		the input file
	 * @param outputFile	the output file
	 */
	public CleanHiCTiming(File inputFile, File outputFile) {
		super(inputFile, new HicTimingLine(null));
		this.outputFile = outputFile;
		isReadyToWrite = false;
		actionName = "Delete the ID and strands columns of the HiC/timing file.";
	}


	@Override
	protected void processCurrentLine() {
		if (isReadyToWrite) {
			try {
				out.write(line.getLine() + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	protected void doFirst() {
		try {
			fw = new FileWriter(outputFile);
			out = new BufferedWriter(fw);
			isReadyToWrite = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	protected void doAtTheEnd() {
		if (out != null) {
			try {
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (fw != null) {
			try {
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
