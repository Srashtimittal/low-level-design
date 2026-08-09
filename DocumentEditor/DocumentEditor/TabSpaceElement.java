// TabSpaceElement represents a tab space in the document.
public class TabSpaceElement implements DocumentElement {
    @Override
    public String render() {
        return "\t";
    }
}
