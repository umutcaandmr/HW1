#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define MAX_CHILDREN 10
#define MAX_WORD_LENGTH 20

// Trie düğüm yapısı
typedef struct TrieNode {
    char value;
    struct TrieNode* children[MAX_CHILDREN];
    bool isEndOfWord;
} TrieNode;

// Yeni bir Trie düğümü oluşturma fonksiyonu
TrieNode* createNode(char value) {
    TrieNode* node = (TrieNode*)malloc(sizeof(TrieNode));
    node->value = value;
    node->isEndOfWord = false;

    for (int i = 0; i < MAX_CHILDREN; i++) {
        node->children[i] = NULL;
    }

    return node;
}

// Trie'ye kelime ekleme fonksiyonu
void insertWord(TrieNode* root, char* word) {
    TrieNode* curr = root;

    for (int i = 0; word[i] != '\0'; i++) {
        int index = word[i] - '0';  // Sayıya karşılık gelen indeksi hesapla

        if (curr->children[index] == NULL) {
            curr->children[index] = createNode(word[i]);
        }

        curr = curr->children[index];
    }

    curr->isEndOfWord = true;
}

// Trie'de kelimeyi arama fonksiyonu
bool searchWord(TrieNode* root, char* word) {
    TrieNode* curr = root;

    for (int i = 0; word[i] != '\0'; i++) {
        int index = word[i] - '0';  // Sayıya karşılık gelen indeksi hesapla

        if (curr->children[index] == NULL) {
            return false;  // Kelime bulunamadı
        }

        curr = curr->children[index];
    }

    return curr != NULL && curr->isEndOfWord;
}

// Trie'deki kelime karşılığını yazdırma fonksiyonu
void printWord(TrieNode* root, char* word, int level) {
    if (root->isEndOfWord) {
        word[level] = '\0';
        printf("%s\n", word);
    }

    for (int i = 0; i < MAX_CHILDREN; i++) {
        if (root->children[i] != NULL) {
            word[level] = root->children[i]->value;
            printWord(root->children[i], word, level + 1);
        }
    }
}

// Sorgu işlemini gerçekleştiren fonksiyon
void processQuery(TrieNode* root, char* number) {
    char word[MAX_WORD_LENGTH];

    for (int i = 0; number[i] != '\0'; i++) {
        int index = number[i] - '0';  // Sayıya karşılık gelen indeksi hesapla

        if (root->children[index] != NULL) {
            word[0] = root->children[index]->value;
            printWord(root->children[index], word, 1);
        } else {
            printf("No words found for the number %s\n", number);
            return;
        }
    }
}

int main() {
    TrieNode* root = createNode('\0');  // Root düğümü oluştur

    // Sözlüğü dosyadan oku ve trie'ye ekle
    FILE* file = fopen("dictionary.txt", "r");

    if (file == NULL) {
        printf("Error opening the dictionary file.\n");
        return 1;
    }

    char word[MAX_WORD_LENGTH];
    while (fgets(word, sizeof(word), file)) {
        // Satır sonunda yeni satır karakterini kaldır
        if (word[strlen(word) - 1] == '\n') {
            word[strlen(word) - 1] = '\0';
        }

        insertWord(root, word);
    }

    fclose(file);

    // Sorgu yap
    char number[MAX_WORD_LENGTH];
    printf("Enter a number: ");
    scanf("%s", number);

    processQuery(root, number);

    return 0;
}

