package menu;

/**
 * Tasl interface.
 *
 * @param <T>
 */
public interface Task<T> {

    /**
     * Runs the task.
     *
     * @return return value for running.
     */
    T run();
}
