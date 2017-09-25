import java.util.HashMap;
import java.util.Set;

public class lcp_trie {
	
	trieNode root;
	public class trieNode
	{
		HashMap<Character, trieNode> children;
		boolean endofword;
		
		trieNode()
		{
			children = new HashMap<Character, trieNode>();
			endofword = false;
		}
	}
	
	lcp_trie()
	{
		root = new trieNode();
	}
	
	public void add(String str)
	{
		trieNode current = root;
		for(int i = 0; i < str.length(); i++)
		{
			char ch = str.charAt(i);
			trieNode node = current.children.get(ch);
			if(node == null)
			{
				node = new trieNode();
				current.children.put(ch, node);
			}
			current = node;
		}
		current.endofword = true;
	}
	
	public boolean search(String str)
	{
		trieNode current = root;
		
		for(int i = 0; i < str.length(); i++)
		{
			char ch = str.charAt(i);
			trieNode node = current.children.get(ch);
			if(node == null)
				break;
			else
			current = node;
		}
		return current.endofword;
	}
	
	public void lcp()
	{
		trieNode current = root;
		StringBuffer prefix = new StringBuffer(); 
		while(current != null && current.children.size() == 1 && !current.endofword)
		{
			Set set = current.children.keySet();
			String key = set.iterator().next().toString();
			trieNode node = current.children.get(key.charAt(0));
			current = node;
			prefix.append(key.charAt(0));
		}
		System.out.println(prefix.toString());
	}
	
	public boolean delete(trieNode current, String word, int index)
	{
		if(index == word.length())
		{
			if(!current.endofword)
			{
				return false;	
			}
		current.endofword = false;
		return current.children.size() == 0;
		}
		char ch = word.charAt(index);
		trieNode node = current.children.get(ch);
		
		if(node == null)
		{
			return false;	
		}
		
		boolean shoulddelete = delete(node, word, index+1);
		
		if(shoulddelete)
		{
			current.children.remove(ch);
			return current.children.size() == 0 && !current.endofword;
		}
		return false;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		lcp_trie trie = new lcp_trie();
		
		trie.add("codable");
		trie.add("code");
		trie.add("coder");
		trie.add("coding");
		
		System.out.println(trie.search("codable"));
		System.out.println(trie.search("codingpad"));
		System.out.println(trie.search("cod"));
		
		trie.lcp();
		
		System.out.println(trie.delete(trie.root, "codable", 0));
		
		System.out.println(trie.search("codable"));

	}

}
