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
package core.action.file;

import java.io.File;

import core.action.Action;
import core.fileLine.Line;


/**
 * @author Nicolas Fourel
 * @version 0.1
 * @param <K>
 */
public abstract class LineFileReader<K> extends Action implements FileReader {

	/** Generated serial version ID */
	private static final long serialVersionUID = 1651258870122088018L;

	protected FileExtractor extractor;	// The file extractor.
	protected File file;				// The file.
	protected String nativeLine;		// The current line as it is in the file.
	protected K line;					// The current formatted line.
	protected boolean force;			// Force the processing of the current line.


	/**
	 * Constructor of {@link LineFileReader}
	 * @param file
	 */
	public LineFileReader (File file, K line) {
		this.file = file;
		this.line = line;
		extractor = new FileExtractor(this);
		force = false;
	}


	@Override
	public void processLine(String line) {
		Line castLine = null;
		if (this.line != null) {
			castLine = (Line) this.line;
			castLine.initialize(line);
		}
		if (force || ((castLine != null) && castLine.isValid())) {
			nativeLine = line;
			processCurrentLine();
		}
	}


	/**
	 * Process the current line
	 * @param currentLine the current valid line
	 */
	protected abstract void processCurrentLine();


	@Override
	protected Object compute() {
		extractor.compute();
		return null;
	}


	@Override
	public File getFile() {
		return file;
	}


	@Override
	public void setFile(File file) {
		this.file = file;
	}


	/**
	 * @return the currentLineNumber
	 */
	public int getCurrentLineNumber() {
		return extractor.getCurrentLineNumber();
	}
}
