package md2html;

import java.io.BufferedReader;
import java.io.IOException;

class SectionReader {
    private BufferedReader reader;
    private StringBuilder section;
    private boolean streamEnded;

    public SectionReader(BufferedReader reader) {
        this.reader = reader;
        streamEnded = false;
        section = new StringBuilder();
    }

    public String curSection() {
        return section.toString();
    }

    public void goToNextSection() throws IOException {
        if (streamEnded) {
            throw new IOException("Cannor get next section: reader closed");
        }
        section.setLength(0);
        String curLine = reader.readLine();
        if (curLine == null) {
            close();
            return;
        }
        // Find first not empty line
        while (curLine.length() == 0) {
            curLine = reader.readLine();
        }

        while (curLine.length() != 0) {
            section.append(curLine);
            section.append("\n");
            curLine = reader.readLine();
            if (curLine == null) {
                close();
                break;
            }
        }
    }

    public String nextSection() throws IOException {
        goToNextSection();
        return curSection();
    }

    public boolean ready() throws IOException {
        return !streamEnded;
    }

    public void close() throws IOException {
        streamEnded = true;
        reader.close();
    }
}