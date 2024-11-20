class MancalaMove extends Move {
    private int pitIndex;

    public MancalaMove(int pitIndex) {
        this.pitIndex = pitIndex;
    }

    public int getPitIndex() {
        return pitIndex;
    }

    @Override
    public String toString() {
        return "Move from pit: " + pitIndex;
    }
}