package com.myProject.game.TTT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class IA <M extends Move> {

    private final IA.Algorithm algo;
    private TTT ttt;
    private TTT.Combo combo;
    private TTT.Tile tile;

    private final class IAMoveWrapper {
        M move;
    }

    public static enum Algorithm {
        /** The Minimax algorithm (slowest) */
        MINIMAX,
        /** The Mininma algorithm with alpha-beta pruning */
        ALPHA_BETA,
        /** The Negamax algorithm with alpha-beta pruning */
        NEGAMAX,
        /** The Negascout algorithm (fastest) */
        NEGASCOUT;
    }

    public IA() {
        this(Algorithm.MINIMAX);
    }

    public IA(IA.Algorithm algo) {
        this.algo = algo;
    }
    
    public Move getBestMove() {
        IA.IAMoveWrapper wrapper = new IA.IAMoveWrapper();
        switch (algo) {
            case MINIMAX:
                minimax(wrapper, 0, getDifficulty().getDepth());
                break;
            /*case ALPHA_BETA:
                alphabeta(wrapper, 0, getDifficulty().getDepth(), -maxEvaluateValue(), maxEvaluateValue());
                break;
            case NEGAMAX:
                negamax(wrapper, getDifficulty().getDepth(), -maxEvaluateValue(), maxEvaluateValue());
                break;
            case NEGASCOUT:
            default:
                negascout(wrapper, getDifficulty().getDepth(), -maxEvaluateValue(), maxEvaluateValue());
                break;*/
        }
        return wrapper.move;
    }

    public Difficulty getDifficulty() {
        int depth = getDepth();
        return depth;
    }

    private int getDepth() {
        return 0;
    }

    private final double minimax(final IA.IAMoveWrapper wrapper, int depth, int DEPTH) {
        if (depth == DEPTH) {
            return evaluate();

        } else if (isOver()) {
            // if depth not reach, must consider who's playing
            return (((DEPTH - depth) % 2) == 1 ? -1 : 1) * evaluate();
        }
        M bestMove = null;
        Collection<M> moves = getPossibleMoves();
        if (moves.isEmpty()) {
            return minimax(null, depth + 1, DEPTH);
        }
        if (depth % 2 == DEPTH % 2) {
            double score = -maxEvaluateValue();
            double bestScore = -maxEvaluateValue();
            for (M move : moves) {
                makeMove(move);
                score = minimax(null, depth + 1, DEPTH);
                unmakeMove(move);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
            }
            if (wrapper != null) {
                wrapper.move = bestMove;
            }
            return bestScore;
        } else {
            double score = maxEvaluateValue();
            double bestScore = maxEvaluateValue();
            for (M move : moves) {
                makeMove(move);
                score = minimax(null, depth + 1, DEPTH);
                unmakeMove(move);
                if (score < bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
            }
            if (wrapper != null) {
                wrapper.move = bestMove;
            }
            return bestScore;
        }
    }
    /**
     * List every valid moves for the current player.<br><br>
     * <i>"Improvement (of the alpha beta pruning) can be achieved without
     * sacrificing accuracy, by using ordering heuristics to search parts
     * of the tree that are likely to force alpha-beta cutoffs early."</i>
     * <br>- <a href="http://en.wikipedia.org/wiki/Alpha-beta_pruning#Heuristic_improvements">Alpha-beta pruning on Wikipedia</a>
     * @return
     *         The list of the current player possible moves
     */
    public List<M> getPossibleMoves(){
        List<M> listOfMoves = new ArrayList<>();
        listOfMoves.addAll(ttt.getBoard());

        listOfMoves.remove(tile.getPossision());
        return listOfMoves;
    }
    /**
     * Evaluate the state of the game <strong>for the current player</strong> after a move.
     * The greatest the value is, the better the position of the current player is.
     * @return
     *         The evaluation of the position for the current player
     * @see #maxEvaluateValue()
     */

    public double maxEvaluateValue( ){

        if (combo.isComplete()&&tile.getValue()=="X") {
            return 10;
        }
        if (combo.isComplete() && tile.getValue()=="Y") {
            return -10;
        } else {
            return 0;
        }
    }

    /**
     * The absolute maximal value for the evaluate function.
     * This value must not be equal to a possible return value of the evaluation function.
     * @return
     *         The <strong>non inclusive</strong> maximal value
     * @see #evaluate()
     */

    public double evaluate() {

    }

    public boolean isOver(){
        if(!combo.isComplete()) {
            return true;
        } else {
            return false;
        }
    }



/*        *//**
         * Get the IA difficulty level for the current player
         * @return
         *         The difficulty
         *//*

        public abstract Difficulty getDifficulty();*/

        /*private final double alphabeta(final IA.IAMoveWrapper wrapper, int depth, int DEPTH, double alpha, double beta) {
        if (depth == DEPTH) {
            return evaluate();
        } else if (isOver()) {
            // if depth not reach, must consider who's playing
            return (((DEPTH - depth) % 2) == 1 ? -1 : 1) * evaluate();
        }
        M bestMove = null;
        double score;
        Collection<M> moves = getPossibleMoves();
        if (moves.isEmpty()) {
            return alphabeta(null, depth + 1, DEPTH, alpha, beta);
        }
        if (depth % 2 == DEPTH % 2) {
            for (M move : moves) {
                makeMove(move);
                score = alphabeta(null, depth + 1, DEPTH, alpha, beta);
                unmakeMove(move);
                if (score > alpha) {
                    alpha = score;
                    bestMove = move;
                    if (alpha >= beta) {
                        break;
                    }
                }
            }
            if (wrapper != null) {
                wrapper.move = bestMove;
            }
            return alpha;
        } else {
            for (M move : moves) {
                makeMove(move);
                score = alphabeta(null, depth + 1, DEPTH, alpha, beta);
                unmakeMove(move);
                if (score < beta) {
                    beta = score;
                    bestMove = move;
                    if (alpha >= beta) {
                        break;
                    }
                }
            }
            if (wrapper != null) {
                wrapper.move = bestMove;
            }
            return beta;
        }
    }

    private final double negamax(final IA.IAMoveWrapper wrapper, int depth, double alpha, double beta) {
        if (depth == 0 || isOver()) {
            return evaluate();
        }
        M bestMove = null;
        Collection<M> moves = getPossibleMoves();
        if (moves.isEmpty()) {
            return -negamax(null, depth - 1, -beta, -alpha);
        } else {
            double score = -maxEvaluateValue();
            for (M move : moves) {
                makeMove(move);
                score = -negamax(null, depth - 1, -beta, -alpha);
                unmakeMove(move);
                if (score > alpha) {
                    alpha = score;
                    bestMove = move;
                    if (alpha >= beta) {
                        break;
                    }
                }
            }
            if (wrapper != null) {
                wrapper.move = bestMove;
            }
            return alpha;
        }
    }

    private final double negascout(fr.pixelprose.minimax4j.IA.IAMoveWrapper wrapper, int depth, double alpha, double beta) {
        if (depth == 0 || isOver()) {
            return evaluate();
        }
        List<M> moves = getPossibleMoves();
        double b = beta;
        M bestMove = null;
        if (moves.isEmpty()) {
            return -negascout(null, depth - 1, -beta, -alpha);
        } else {
            double score;
            boolean first = true;
            for (M move : moves) {
                makeMove(move);
                score = -negascout(null, depth - 1, -b, -alpha);
                if (!first && alpha < score && score < beta) {
                    score = -negascout(null, depth - 1, -beta, -alpha);
                }
                unmakeMove(move);
                if (score > alpha) {
                    alpha = score;
                    bestMove = move;
                    if (alpha >= beta) {
                        break;
                    }
                }
                b = alpha + 1;
                first = false;
            }
            if (wrapper != null) {
                wrapper.move = bestMove;
            }
            return alpha;
        }*/

        /**
         * Play the given move and modify the state of the game.
         * This function must set correctly the turn of the next player
         * ... by calling the next() method for example.
         * @param move
         *             The move to play
         * @see #next()
         */
        public abstract void makeMove(M move);

        /**
         * Undo the given move and restore the state of the game.
         * This function must restore correctly the turn of the previous player
         * ... by calling the previous() method for example.
         * @param move
         *             The move to cancel
         * @see #previous()
         */
        public abstract void unmakeMove(M move);





        /**
         * Change current turn to the next player.
         * This method must not be used in conjunction with the makeMove() method.
         * Use it to implement a <strong>pass</strong> functionality.
         * @see #makeMove(Move)
         */
        public abstract void next();

        /**
         * Change current turn to the previous player.
         * This method must not be used in conjunction with the unmakeMove() method.
         * Use it to implement an <strong>undo</strong> functionality.
         * @see #unmakeMove(Move)
         */
        public abstract void previous();
}

    /*public int score() {

        if (combo.isComplete()&&tile.getValue()=="X") {
            return 10;
        }
        if (combo.isComplete() && tile.getValue()=="Y") {
            return -10;
        } else {
            return 0;
        }
    }*/


