package nl.trivento.propertybased.exercise4;

import java.util.Objects;

public class Action {

    private final boolean isAdd;
    private final String element;

    private Action(String element) {
        this.isAdd = element != null;
        this.element = element;
    }

    public static Action createDeleteAction() {
        return new Action(null);
    }

    public static Action createAddAction(String element) {
        return new Action(element);
    }

    public boolean isAdd() {
        return isAdd;
    }

    public boolean isDelete() {
        return !isAdd;
    }

    public String getElement() {
        return element;
    }

    @Override
    public String toString() {
        return "Action{" +
                "isAdd=" + isAdd +
                ", element='" + element + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action action = (Action) o;
        return isAdd == action.isAdd &&
                Objects.equals(element, action.element);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isAdd, element);
    }
}
