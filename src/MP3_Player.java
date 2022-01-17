import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.ImageIcon;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MP3_Player extends JFrame {
    private JButton add_file, play_file, pause, stop_play, repeat, next, previous, clear;
    private JLabel frame_title, playing, track_playing;
    private JPanel panel;
    private JScrollPane track_holder;
    private JTable tracklist;

    long pause_time, duration;
    int count, srow;
    int pause_press = 0;
    int stop_press = 0;
    int repeat_press = 0;
    int play_press = 0;
    int next_press = 0;
    int previous_press = 0;
    int rtype = 0;
    String song_title;
    File songFile;
    Player player;
    FileInputStream finput;
    BufferedInputStream binput;
    DefaultTableModel tblmod;
    File[] files;

    public MP3_Player() {
        super("SIMPLE MP3 PLAYER");
        look();

        setIconImage(new ImageIcon(".\\icons\\Player.png").getImage());
        setPreferredSize(new Dimension(1034, 556));
        setResizable(false);
        setBounds(0, 0, 991, 470);
        getContentPane().setLayout(null);

        comp();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    void look(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
    }

    private void comp() {

        panel = new JPanel();
        frame_title = new JLabel("SIMPLE MP3 PLAYER");
        add_file = new JButton();
        play_file = new JButton();
        pause = new JButton();
        stop_play = new JButton();
        playing = new JLabel("NOW PLAYING:");
        repeat = new JButton();
        next = new JButton();
        previous = new JButton();
        clear = new JButton();
        track_playing = new JLabel();
        track_holder = new JScrollPane();
        tracklist = new JTable();

        //PANEL
        panel.setBackground(new Color(0, 102, 128));
        panel.setLayout(null);

        //TITLE LABEL
        frame_title.setFont(new Font("Copperplate Gothic Light", 3, 24));
        frame_title.setForeground(Color.WHITE);
        panel.add(frame_title);
        frame_title.setBounds(380,10, 300, 40);

        //ADD FILE BUTTON
        add_file.setBackground(new Color(0, 102, 128));
        add_file.setIcon(new ImageIcon(".\\icons\\Folder.png"));
        add_file.setMargin(new Insets(0, 0, 0, 0));
        add_file.setBorder(null);
        add_file.setPreferredSize(new Dimension(95, 29));
        add_file.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actions(e);
            }
        });
        panel.add(add_file);
        add_file.setBounds(860, 60, 112, 70);

        //PLAY BUTTON
        play_file.setBackground(new Color(0, 102, 128));
        play_file.setIcon(new ImageIcon(".\\icons\\Play.png"));
        play_file.setMargin(new Insets(0, 0, 0, 0));
        play_file.setBorder(null);
        play_file.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                play_press = 1;
                actions(e);
            }
        });
        panel.add(play_file);
        play_file.setBounds(640, 270, 80, 80);

        //PAUSE BUTTON
        pause.setBackground(new Color(0, 102, 128));
        pause.setIcon(new ImageIcon(".\\icons\\Pause.png"));
        pause.setMargin(new Insets(0, 0, 0, 0));
        pause.setBorder(null);
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pause_press = 1;
                actions(e);
            }
        });
        panel.add(pause);
        pause.setBounds(750, 270, 80, 80);

        //STOP BUTTON
        stop_play.setBackground(new Color(0, 102, 128));
        stop_play.setIcon(new ImageIcon(".\\icons\\Stop.png"));
        stop_play.setMargin(new Insets(0, 0, 0, 0));
        stop_play.setBorder(null);
        stop_play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stop_press = 1;
                actions(e);
            }
        });
        panel.add(stop_play);
        stop_play.setBounds(860, 270, 80, 80);

        //REPEAT BUTTON
        repeat.setBackground(new Color(0, 102, 128));
        repeat.setIcon(new ImageIcon(".\\icons\\Repeat All.png"));
        repeat.setMargin(new Insets(0, 0, 0, 0));
        repeat.setBorder(null);
        repeat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repeat_press = 1;

                //REPEAT OFF
                if (rtype == 2){
                    repeat.setBackground(new Color(0, 102, 128));
                    rtype = 0;
                }

                //REPEAT ONE
                else if (rtype == 0){
                    repeat.setBackground(Color.WHITE);
                    repeat.setIcon(new ImageIcon(".\\icons\\Repeat Current.png"));
                    rtype = 1;
                    actions(e);
                }

                //REPEAT ALL
                else if (rtype == 1){
                    repeat.setBackground(Color.WHITE);
                    repeat.setIcon(new ImageIcon(".\\icons\\Repeat All.png"));
                    rtype = 2;
                    actions(e);
                }

            }
        });
        panel.add(repeat);
        repeat.setBounds(750, 360, 80, 80);

        //NEXT BUTTON
        next.setBackground(new Color(0, 102, 128));
        next.setIcon(new ImageIcon(".\\icons\\Next.png"));
        next.setMargin(new Insets(0, 0, 0, 0));
        next.setBorder(null);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int next_press = 1;
                actions(e);
            }
        });
        panel.add(next);
        next.setBounds(850, 360, 80, 80);

        //PREVIOUS BUTTON
        previous.setBackground(new Color(0, 102, 128));
        previous.setIcon(new ImageIcon(".\\icons\\Previous.png"));
        previous.setMargin(new Insets(0, 0, 0, 0));
        previous.setBorder(null);
        previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int previous_press = 1;
                actions(e);
            }
        });
        panel.add(previous);
        previous.setBounds(660, 360, 80, 80);

        //CLEAR BUTTON
        clear.setBackground(Color.WHITE);
        clear.setForeground(Color.BLACK);
        clear.setFont(new Font("Times New Roman", 3, 14));
        clear.setText("CLEAR TRACKLIST");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actions(e);
            }
        });
        panel.add(clear);
        clear.setBounds(120, 420, 180, 60);

        //NOW PLAYING LABEL
        playing.setFont(new Font("Times New Roman", 3, 18));
        playing.setPreferredSize(new Dimension(134, 21));
        panel.add(playing);
        playing.setBounds(430, 170, 139, 37);

        //CHOSEN TRACK LABEL
        track_playing.setFont(new Font("Times New Roman", 3, 18));
        track_playing.setForeground(Color.WHITE);
        panel.add(track_playing);
        track_playing.setVisible(false);
        track_playing.setBounds(580, 170, 400, 37);

        //TRACKLIST TABLE
        tracklist.setFont(new Font("Times New Roman", 2, 14));
        tracklist.setBackground(Color.BLACK);
        tracklist.setForeground(Color.WHITE);
        tracklist.setModel(tblmod = new DefaultTableModel(
                new String[][]{

                },
                new String [] {
                        "TRACKLIST"
                }
        )
        {
            boolean[] canEdit = new boolean[] {
                    false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }

        });
        tracklist.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mouse_action(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        //TRACKLIST HOLDER SCROLLPANE
        track_holder.setViewportView(tracklist);
        track_holder.getViewport().setBackground(Color.BLACK);
        if (tracklist.getColumnModel().getColumnCount() > 0) {
            tracklist.getColumnModel().getColumn(0).setResizable(false);
        }
        panel.add(track_holder);
        track_holder.setBounds(10, 60, 400, 320);

        getContentPane().add(panel);
        panel.setBounds(0, 0, 1070, 560);

        pack();
    }

    //PLAY METHOD
    void play_song(File location){
        try {
            finput = new FileInputStream(location);
            binput = new BufferedInputStream(finput);

            player = new Player(binput);

            duration = finput.available();

            new Thread() {
                public void run(){
                    try {
                        player.play();

                        //REPEATING THE SAME FILE
                        if (player.isComplete() && count == 1) {
                            play_song(songFile);
                        }

                        //GOING THROUGH THE REST OF THE PLAYLIST
                        else if ((player.isComplete() && stop_play.getAction() == null) || rtype == 2){
                            srow++;
                            track_chooser();

                            //REPEATING THE ENTIRE PLAYLIST
                            if(rtype == 2 && srow == tracklist.getRowCount() && player != null){

                                if ((pause_press != 1 && stop_press != 1)) {

                                    tracklist.setRowSelectionInterval(0, 0);
                                    srow = tracklist.getSelectedRow();

                                    track_chooser();
                                }
                            }
                        }

                        //FILE EXECUTION COMPLETE
                        else if (player.isComplete() && rtype != 2)
                            track_playing.setVisible(false);

                    }
                    catch (JavaLayerException javaLayerException) {
                    }
                }
            }.start();

        } catch (JavaLayerException | IOException e) {
        }

    }

    //STOP METHOD
    void stop_song(){
        if (stop_press == 1){
            stop_press = 0;
            next_press = 0;
            previous_press = 0;
            if (player != null){
                if (rtype == 2){
                    rtype = 0;

                    player.close();
                    track_playing.setVisible(false);

                    duration = 0;
                    pause_time = 0;

                    rtype = 2;
                }

                else {
                    player.close();
                    track_playing.setVisible(false);

                    duration = 0;
                    pause_time = 0;
                }
            }
        }
    }

    //PAUSE METHOD
    void pause_song(){
        if (pause_press == 1){
            if (player != null){
                try {
                    if (rtype == 2){
                        rtype = 0;
                        pause_time = finput.available();
                        player.close();
                        rtype = 2;
                    }

                    else {
                        pause_time = finput.available();
                        player.close();
                    }

                } catch (IOException e) {
                }
            }
        }
    }

    //RESUME METHOD - 1.1
    void resume_song(){
        play_press = 0;
        if (pause_press == 1 && stop_press == 0){
            if (rtype == 2){
                rtype = 0;
                resuming();
                rtype = 2;
            }

            else {
                resuming();
            }
        }

    }

    //RESUME METHOD - 1.2
    void resuming(){
        try {
            pause_press = 0;

            finput = new FileInputStream(songFile);
            binput = new BufferedInputStream(finput);

            player = new Player(binput);

            finput.skip(duration - pause_time);

            new Thread() {
                @Override
                public void run(){
                    try {
                        player.play();

                        //REPEATING THE SAME FILE
                        if (player.isComplete() && count == 1) {
                            play_song(songFile);
                        }

                        //GOING THROUGH THE REST OF THE PLAYLIST
                        else if ((player.isComplete() && stop_play.getAction() == null) || rtype == 2){
                            srow++;
                            track_chooser();

                            //REPEATING THE ENTIRE PLAYLIST
                            if(rtype == 2 && srow == tracklist.getRowCount() && player != null){

                                if ((pause_press != 1 && stop_press != 1)) {

                                    tracklist.setRowSelectionInterval(0, 0);
                                    srow = tracklist.getSelectedRow();

                                    track_chooser();
                                }
                            }
                        }

                        //FILE EXECUTION COMPLETE
                        else if (player.isComplete() && rtype != 2)
                            track_playing.setVisible(false);
                    }
                    catch (JavaLayerException javaLayerException) {
                    }
                }
            }.start();

        } catch (JavaLayerException | IOException | NullPointerException e) {
        }
    }

    void actions(ActionEvent ae){
        //ADD FILES
        if (ae.getSource() == add_file){
            filechoice();
        }

        //STOP
        if (ae.getSource() == stop_play){
            if (rtype == 2){
                rtype = 0;
                stop_song();
                rtype = 2;
            }

            else {
                stop_song();
            }
        }

        //PAUSE
        if (ae.getSource() == pause){
            pause_song();
        }

        //REPEAT
        if (ae.getSource() == repeat){
            if (repeat_press == 1){
                switch (count) {
                    case 0:
                        count = 1;
                        break;
                    case 1:
                        count = 0;
                        break;
                }
            }
        }

        //NEXT TRACK
        if (ae.getSource() == next){
            stop_press = 1;

            if (rtype == 2){
                rtype = 0;

                stop_song();

                if ((srow + 1) < tracklist.getRowCount()){
                    srow = srow + 1;
                }

                else {
                    srow = 0;
                }

                track_chooser();

                rtype = 2;
            }

            else{
                stop_song();

                if ((srow + 1) < tracklist.getRowCount()){
                    srow = srow + 1;
                }

                else {
                    srow = 0;
                }

                track_chooser();
            }
        }

        //PREVIOUS TRACK
        if (ae.getSource() == previous){
            stop_press = 1;

            if (rtype == 2){
                rtype = 0;
                stop_song();

                if ((srow - 1) >= 0){
                    srow = srow - 1;
                }

                else {
                    srow = tracklist.getRowCount() - 1;
                }

                track_chooser();

                rtype = 2;
            }

            else{
                stop_song();

                if ((srow - 1) >= 0){
                    srow = srow - 1;
                }

                else {
                    srow = tracklist.getRowCount() - 1;
                }

                track_chooser();
            }
        }

        //CLEAR TRACKLIST
        if (ae.getSource() == clear){
            if (rtype == 2){
                rtype = 0;

                stop_press = 1;
                stop_song();
                tblmod.setRowCount(0);

                rtype = 2;
            }

            else{
                stop_press = 1;
                stop_song();
                tblmod.setRowCount(0);
            }
        }

        //RESUME
        if (play_press == 1){
            if (ae.getSource() == play_file){
                resume_song();
            }
        }
    }

    void filechoice(){
        try {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("MP3 Files", "mp3");
            chooser.setFileFilter(filter);
            chooser.setMultiSelectionEnabled(true);
            chooser.showOpenDialog(null);
            File[] f_temp = chooser.getSelectedFiles();

            int x = 0;
            int z;
            if (tracklist.getRowCount() == 0){
                z = 0;
                files = new File[100];
            }
            else {
                z = tracklist.getRowCount();
            }

            while (x < f_temp.length){
                files[z] = f_temp[x];

                x++;
                z++;
            }

            String[] tbl_track = new String[1];
            String track_title;
            tblmod.setRowCount(0);
            for (File f : files){
                track_title = f.getName();
                tbl_track[0] = track_title.substring(0, track_title.lastIndexOf("."));
                tblmod.addRow(tbl_track);
            }
        }
        catch (NullPointerException e){
        }
    }

    void track_chooser(){
        if (srow < tracklist.getRowCount()){
            String chosen_track = tracklist.getValueAt(srow, 0).toString() + ".mp3";
            int x = 0;

            while (x < files.length){
                if (files[x].getName().equals(chosen_track)){
                    songFile = files[x];
                    break;
                }

                x++;

            }

            song_title = songFile.getName().substring(0, songFile.getName().lastIndexOf("."));
            track_playing.setText(song_title);
            track_playing.setVisible(true);
            play_song(songFile);
        }
    }

    void mouse_action(MouseEvent me){
        //PLAY
        if (me.getSource() == tracklist){
            if (me.getClickCount() == 2){
                stop_press = 1;

                if (rtype == 2){
                    rtype = 0;
                    stop_song();
                    srow = tracklist.getSelectedRow();
                    track_chooser();
                    rtype = 2;
                }

                else{
                    stop_song();
                    srow = tracklist.getSelectedRow();
                    track_chooser();
                }

            }
        }


    }

    public static void main(String args[]) {
        new MP3_Player();
    }
}
