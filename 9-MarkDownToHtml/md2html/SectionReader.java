package md2html;

import java.io.BufferedReader;
import java.io.IOException;

class SectionReader {
    private BufferedReader reader;
    private StringBuilder section;
    private boolean sectionReaderClosed;

    public SectionReader(BufferedReader reader) {
        this.reader = reader;
        sectionReaderClosed = false;
        section = new StringBuilder();
    }

    public String curSection() {
        return section.toString();
    }

    public void goToNextSection() throws IOException {
        if (sectionReaderClosed) {
            throw new IOException("Cannor read next section: reader closed");
        }
        section.setLength(0);
        String curLine = reader.readLine();
        if (curLine == null) {
            close();
            return;
        }

        while (curLine.length() == 0) {
            curLine = reader.readLine();
        }

        while (curLine.length() != 0) {
            section.append(curLine);
            section.append(System.lineSeparator());
            curLine = reader.readLine();
            if (curLine == null) {
                close();
                break;
            }
        }

        section.delete(section.length() - System.lineSeparator().length(), section.length());
    }

    public String nextSection() throws IOException {
        goToNextSection();
        return curSection();
    }

    public boolean ready() {
        return !sectionReaderClosed;
    }

    public void close() {
        sectionReaderClosed = true;
    }
}