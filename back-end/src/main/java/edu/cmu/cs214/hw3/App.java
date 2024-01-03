package edu.cmu.cs214.hw3;

import java.io.IOException;
import java.util.Map;
import fi.iki.elonen.NanoHTTPD;

/**
 * Hello world!
 *
 */
public class App extends NanoHTTPD {
    private SantoriniGame game;
    private static final int NUM = 8080;

    public App() throws IOException {
        super(NUM);

        this.game = new SantoriniGame();

        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning!\n");
    }

    public static void main(String[] args) {
        try {
            new App();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Map<String, String> params = session.getParms();
        if (uri.equals("/newgame")) {
            this.game = new SantoriniGame();
        } else if (uri.equals("/newgodgame")) {
            this.game = new SantoriniGame(params.get("p1"), params.get("p2"));
        } else if (uri.equals("/p1w1")) {
            this.game.initialize(1, 1, Integer.parseInt(params.get("x")), Integer.parseInt(params.get("y")));
        } else if (uri.equals("/p1w2")) {
            this.game.initialize(1, 2, Integer.parseInt(params.get("x")), Integer.parseInt(params.get("y")));
        } else if (uri.equals("/p2w1")) {
            this.game.initialize(2, 1, Integer.parseInt(params.get("x")), Integer.parseInt(params.get("y")));
        } else if (uri.equals("/p2w2")) {
            this.game.initialize(2, 2, Integer.parseInt(params.get("x")), Integer.parseInt(params.get("y")));
        } else if (uri.equals("/selectW")) {
            this.game.selectWorker(Integer.parseInt(params.get("x")), Integer.parseInt(params.get("y")));
        } else if (uri.equals("/move")) {
            this.game.move(Integer.parseInt(params.get("p")), Integer.parseInt(params.get("w")),
                    Integer.parseInt(params.get("x")), Integer.parseInt(params.get("y")));
        } else if (uri.equals("/build")) {
            this.game.build(Integer.parseInt(params.get("p")), Integer.parseInt(params.get("w")),
                    Integer.parseInt(params.get("x")), Integer.parseInt(params.get("y")), "block");
        } else if (uri.equals("/pass")) {
            this.game.pass();
        }
        // Extract the view-specific data from the game and apply it to the template.
        GameState gameplay = GameState.forGame(this.game);
        return newFixedLengthResponse(gameplay.toString());
    }
}