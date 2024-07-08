package com.cc.leetCode;

import java.util.*;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: WordLadder2
 * https://leetcode.cn/problems/word-ladder-ii/
 * 126. 单词接龙 II
 * <p>
 * 1 <= beginWord.length <= 5
 * endWord.length == beginWord.length
 * 1 <= wordList.length <= 500
 * wordList[i].length == beginWord.length
 * beginWord、endWord 和 wordList[i] 由小写英文字母组成
 * beginWord != endWord
 * wordList 中的所有单词 互不相同
 * @date: 2023-06-30
 */

public class WordLadder2 {

    /**
     * 示例：
     * 输入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
     * 输出：[["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]
     * 解释：存在 2 种最短的转换序列：
     * "hit" -> "hot" -> "dot" -> "dog" -> "cog"
     * "hit" -> "hot" -> "lot" -> "log" -> "cog"
     *
     *  hit
     *  hot
     *  dot, lot
     *  dog, log
     *
     * @param args
     */

    public static void main(String[] args) {
        String beginWord = "hit";
        String endWord = "cog";
        List<String> wordList = Arrays.asList(new String[]{"hot", "dot", "dog", "lot", "log", "cog"});
        System.out.println(new WordLadder2().findLadders(beginWord, endWord, wordList));

        beginWord = "hit";
        endWord = "cok";
        wordList = Arrays.asList(new String[]{"hot", "dot", "dog", "lot", "log", "cog", "cok"});
        System.out.println(new WordLadder2().findLadders(beginWord, endWord, wordList));

        beginWord = "hit";
        endWord = "cog";
        wordList = Arrays.asList(new String[]{"hot","dot","dog","lot","log"});
        System.out.println(new WordLadder2().findLadders(beginWord, endWord, wordList));
    }

    // 广度优先遍历
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {

        List<List<String>> shortPath = new ArrayList<>();

        // endWord不存在于wordList中
        if (wordList.stream().noneMatch(s -> s.equals(endWord))) {
            return shortPath;
        }

        Set<String> reached = new HashSet<>();
        List<String> todoList = new ArrayList<>();
        Set<String> todoSet = new HashSet<>();
        Map<String,List<String>> parent = new HashMap<>();

        todoList.add(beginWord);
        reached.add(beginWord);

        boolean find = false;
        // todoList中要处理的数据 [beginIndex, endIndex)
        int beginIndex = 0;
        int endIndex = todoList.size();

        // 遍历 todoList 中的[beginIndex, endIndex)
        while (beginIndex < endIndex && !find) {
            // 这一轮的节点不再重复处理
            reached.addAll(todoList.subList(beginIndex, endIndex));

            // 遍历 todoList
            for (int i = beginIndex; i < endIndex; i++) {
                // 挨个与还没有处理过的单词进行比较
                String todoWord = todoList.get(i);
                for (int toCompareIndex = 0; toCompareIndex < wordList.size(); toCompareIndex++) {
                    String toCompareWord = wordList.get(toCompareIndex);
                    if (reached.contains(toCompareWord)) {
                        continue;
                    }

                    // wordList.get(toCompareIndex) 可达，父节点是 todoList.get(i)
                    if (isOneDiff(todoWord, toCompareWord)) {
                        todoSet.add(toCompareWord);
                        addParent(parent, toCompareWord, todoWord);
                        if (toCompareWord.equals(endWord)) {
                            // 已经在当前层找到最短路径了，不用再进行todoList中的下一轮了，但是当前轮还是得遍历完，可能存在多个最短路径。
                            find = true;
                        }
                    }
                }
            }

            todoList.addAll(todoSet);
            todoSet.clear();

            // 下一步接着遍历
            beginIndex = endIndex;
            endIndex = todoList.size();
        }

        if (find) {
            List<List<String>> paths = buildPath(parent, endWord);
            shortPath.addAll(paths);
        }

        return shortPath;
    }

    private void addParent(Map<String,List<String>> parent, String childWord, String parentWord) {
        if (parent.get(childWord) == null) {
            parent.put(childWord, new ArrayList<>());
        }
        parent.get(childWord).add(parentWord);
    }

    // root 到节点 i 的路径
    private List<List<String>> buildPath(Map<String,List<String>> parent, String thisWord) {
        // thisWord 没有父节点， i就是root
        List<List<String>> result = new ArrayList<>();
        if (parent.get(thisWord) == null) {
            result.add(new ArrayList<String>() {{
                add(thisWord);
            }});
            return result;
        }

        // thisWord 有父节点
        for (String oneParent : parent.get(thisWord)) {
            List<List<String>> parentPath = buildPath(parent, oneParent);
            for (List<String> path : parentPath) {
                path.add(thisWord);
            }
            result.addAll(parentPath);
        }
        return result;
    }

    private boolean isOneDiff(String word1, String word2) {
        int diff = 0;
        char[] arr1 = word1.toCharArray();
        char[] arr2 = word2.toCharArray();
        for (int i = 0; i < word1.length() && diff < 2; i++) {
            if (arr1[i] != arr2[i]) {
                diff++;
            }
        }
        return diff == 1;
    }
}
