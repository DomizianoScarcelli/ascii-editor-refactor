package controller.commands.copycutpaste;

import view.MainPanel;

public class MoveCommandAlt extends PasteCommand {
    private MainPanel mainPanel = MainPanel.getInstance();

    public MoveCommandAlt(int x2, int y2) {
        super(x2, y2);
    }

    @Override
    public void execute() {
        if (mainPanel.getCurrentMove() != null) mainPanel.getCurrentMove().undo();
        new CutCommand().execute();
        super.execute();
        mainPanel.setCurrentMove(this);

    }

    @Override
    public void undo() {
        super.undo();
    }
}
