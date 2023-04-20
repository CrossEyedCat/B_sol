import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static String lineCon(String str) {
        String a = "";
        Map < Character, Integer > c = new HashMap < > ();
        c.put('!', 5);
        c.put('&', 4);
        c.put('|', 3);
        c.put('-', 2);
        c.put('(', 1);

        List < Character > d = new ArrayList < > ();
        for (char e: str.toCharArray()) {
            switch (e) {
                case '!':
                case '&':
                case '|':
                case '-':
                    while (!d.isEmpty() && c.get(d.get(d.size() - 1)) > c.get(e)) {
                        a += d.get(d.size() - 1);
                        d.remove(d.size() - 1);
                    }
                    d.add(e);
                    break;
                case '(':
                    d.add(e);
                    break;
                case ')':
                    while (d.get(d.size() - 1) != '(') {
                        a += d.get(d.size() - 1);
                        d.remove(d.size() - 1);
                    }
                    d.remove(d.size() - 1);
                    break;
                default:
                    if (Character.isLetterOrDigit(e) || e == '\'' || e == '@' || e == '#' || e == '~') {
                        a += e;
                    }
                    break;
            }
        }

        while (!d.isEmpty()) {
            a += d.get(d.size() - 1);
            d.remove(d.size() - 1);
        }

        return a;
    }
    public static int bool_solver(String str) {
        int index = 1;
        while (str.length() != 1) {
            switch (str.charAt(index)) {
                case '!':
                    str = str.substring(0, index - 1) + (str.charAt(index - 1) == '1' ? "0" : "1") + str.substring(index + 1);
                    index--;
                    break;
                case '&':
                    str = str.substring(0, index - 2) + ((str.charAt(index - 1) == '1' && str.charAt(index - 2) == '1') ? "1" : "0") + str.substring(index + 1);
                    index -= 2;
                    break;
                case '|':
                    str = str.substring(0, index - 2) + ((str.charAt(index - 1) == '0' && str.charAt(index - 2) == '0') ? "0" : "1") + str.substring(index + 1);
                    index -= 2;
                    break;
                case '-':
                    str = str.substring(0, index - 2) + ((str.charAt(index - 1) == '0' && str.charAt(index - 2) == '1') ? "0" : "1") + str.substring(index + 1);
                    index -= 2;
                    break;
                default:
                    index++;
            }
        }
        return str.charAt(0) == '1' ? 1 : 0;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String main_l = scanner.nextLine();
        String line_next = "";
        boolean flag = true;
        for (int l = 0; l < main_l.length(); l++) {
            if (Character.isLetterOrDigit(main_l.charAt(l)) && flag && main_l.charAt(l) != '-') {
                line_next += '~';
                flag = false;
            }
            if (!(Character.isLetterOrDigit(main_l.charAt(l))) && !flag) {
                line_next += '~';
                flag = true;
            }
            if (main_l.charAt(l) == '>') {

            } else if (main_l.charAt(l) == '0') {
                line_next += '@';
            } else if (main_l.charAt(l) == '1') {
                line_next += '#';
            } else {
                line_next += main_l.charAt(l);
            }
        }
        if (Character.isLetterOrDigit(main_l.charAt(main_l.length() - 1))) {
            line_next += '~';
        }
        main_l = line_next;
        Pattern c = Pattern.compile("[A-Za-z0-9'@#~]+");
        Set < String > strings = new HashSet < String > ();
        Matcher w_start = c.matcher(main_l);
        while (w_start.find()) {
            String group = w_start.group();
            strings.add(group);
        }
        int k_1 = 0, k_0 = 0;
        main_l = lineCon(main_l);
        int m_s = (int) Math.pow(2, strings.size());
        for (int cur = 0; cur < m_s; cur++) {
            String cur_main_l = main_l;
            int cur_copy = cur;
            List < String > cur_list = new ArrayList < String > (strings);
            for (String str: cur_list) {
                cur_main_l = cur_main_l.replaceAll(str, cur_copy % 2 == 1 ? "1" : "0");
                cur_copy = cur_copy / 2;
            }
            if (bool_solver(cur_main_l) == 1) {
                k_1++;
            } else {
                k_0++;
            }
        }
        if (k_1 == m_s) {
            System.out.println("Valid");
        } else if (k_0 == m_s) {
            System.out.println("Unsatisfiable");
        } else {
            System.out.println("Satisfiable and invalid, " + k_1 + " true and " + k_0 + " false cases");
        }
    }
}