import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;


class Solution {
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> st = new Stack<Integer>();

        for (int asteroid : asteroids) {
            boolean flag = true;
            while (!st.isEmpty() && (st.peek() > 0 && asteroid < 0)) { // executes if - asteroid found and there is a + astroid in stack to intersect
                if (Math.abs(st.peek()) < Math.abs(asteroid)) { //if + < -, pop the +
                    st.pop();
                    continue; // skips remaining loop content, pops all + asteroids until current - destroyed or neg found in stack so exit loop and add curr - to stack
                }

                else if (Math.abs(st.peek()) == Math.abs(asteroid)) { // both destroyed
                    st.pop();
                }

                flag = false; // - < +, don't add - to stack
                break; // the difference between continue vs break is cont will skip remaining loop content for current iteration, but break will exit the loop entirely
                // such that if +'s found in stack that are < - it will continue looping but skip the flag statement until either no more + or a + > - found so the loop
                // is broken and move to next asteroid
            }

            if (flag) { // will add preceding - asteroids and then + asteroids until neg found
                st.push(asteroid);
            }
        }

        // Add the asteroids from the stack to the vector in the reverse order.
        int[] remainingAsteroids = new int[st.size()];
        for (int i = remainingAsteroids.length - 1; i >= 0; i--) {
            remainingAsteroids[i] = st.peek();
            st.pop();
        }

        return remainingAsteroids;
    }
}


// mine. this got ugly with when positive asteroid on R, no longer needed  so store, but consecutive negs also stored
// separatly to pop then when + astrooid cmae theen all +s ended in ans and all remaining negs in the temp but things
// are all reversed and its bad
class Solution {
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> s = new Stack<>();
        ArrayList<Integer> ans = new ArrayList<>();
        Stack<Integer> temp = new Stack<>();

        for (int a : asteroids) {
            s.push(a);
        }

        while (!s.isEmpty()) {
            if (s.peek() > 0) {
                ans.add(s.pop());
            } else {
                while (s.peek() < 0) {
                    temp.push(s.pop());
                }
                int a = s.peek();
                while (!temp.isEmpty()) {
                    int b = temp.peek();
                    if (Math.abs(a) < Math.abs(b)) {
                        s.pop();
                        break;
                    }
                    temp.pop();
                }

            }
        }
        while(ans.remove() != null){
            s.push(ans.remove());
        }
        while(!temp.isEmpty()){
            s.push(temp.pop());
        }
        while(!s.isEmpty()){
            ans.add(s.pop());
        }
        return ans.toArray(new int[ans.size()]);


    }
}


// prior to regularr loop if final int + then pop since will never have opposing asteroid
// [5,10,-5,1]

// must push all consecutive same sign ints because all will intersect until only same sign remain
//[5,10,-5,-10,-20] must skip overr  -20 -10 and push onto a temp stack? so when non same sign found (10,-5)
// handle rsult = 10 and then pop temp stack item = 10, both explode, 5 is different sign than temp.peek -20 so
// wee can process

// what if temp stack neg then pos? will never need to store positives in LIFO analysis, once furthest right
// then no neg will ever intersect sinc + -> R, - goes L.  just like first condition above of eliminating + on
// far R
// [5, -1, 22, 12, -5, -10, -11]   -5 -10 -11 all whiped. 12 and 22 data stored but now irreleevant to interseections. -1 gone, 5 stored. stored in orrderr 12,22,5. must rev.
//