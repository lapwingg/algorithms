// Czajka Kamil grupa nr 1
#include <iostream>
#include <cmath>
using namespace std;

class PrimeNumber{
	public:
		long long value;
		long long squareOfValue;
		PrimeNumber* next;

		PrimeNumber(long long v) : value(v){
			next = NULL;
			squareOfValue = value * value;		// kwadrat liczby pierwszej = potrzebny do sprawdzania warunkow 
		}

		~PrimeNumber(){}
};

class Compartment{
	public:
		long long wspX;
		long long wspY;
		PrimeNumber* list;
		PrimeNumber* last;

		Compartment(long long b, long long e){
			list = NULL;			
			last = NULL;
			wspX = b;
			wspY = e;

			long double sqr = sqrt ( (long double) wspY );

			if (wspY > 10000){					// dla bardzo krótkich przedziałów miałem błąd wykonania w segmentedSieve
				segmentedSieve( (long long) (sqr + 1.0) );
			}
			else{
				eratosthenesSieve( (long long) (sqr + 1.0) );
			}
		}

		void eratosthenesSieve(long long b){
			bool* array = new bool[b - 1];

			array[0] = true;		// tylko liczby nieparzyste i 2
			for (long long i = 3; i <= b; i += 2){
				array[i - 2] = true;
			}

			long long j;
			long long k = (long long) sqrt ( (long double) b );
			for (long long i = 3; i <= k; i++){			// jesli TRUE to wiem ze jestem w liczbie pierwszej
				if (array[i - 2] == true){		// wykreslam jej wiekrtnosci az do konca tablicy
					j = i + i;
					while (j < b){
						array[j - 2] = false;
						j += i;
					}
				}
			}

			addElement(2);			// spisuje wynik 2 i kolejne nieparzyste jakie pozostale
			for (long long i = 3; i <= b; i += 2){
				if (array[i - 2] == true){
					addElement(i);	
				}
			}

			if (array != NULL){
				delete[] array;
			}
		}

		void segmentedSieve(long long b){
			long long m = (long long) sqrt ( (long double) b);
			bool* array = new bool[m];

			array[0] = true;		// liczba 2
			for (int i = 3; i <= m; i += 2){	// kolejne nieparzyste - domyslnie FALSE
				array[i - 2] = true;
			}

			long long j;	// czesc I - zwykle sito erastotenesa dla przedzialu 2..sqrt(m)
			long long mm = (long long) sqrt ( (long double) m);
			for (long long i = 3; i <= mm; i++){
				if (array[i - 2] == true){
					j = i + i;
					while (j <= m){
						array[j - 2] = false;
						j += i;
					}
				}
			}

			addElement(2);		// 2 i kolejne nieparzyste do listy
			for (long long i = 3; i <= m; i += 2){
				if (array[i - 2] == true){
					addElement(i);
				}
			}
			
			long long begin = m + 1, end = begin + m - 1;
			long long endd;
			PrimeNumber* walk;
			while (true){	// koniec gdy osiagne wartosc >= parametru funkcji
				long long i = 0;
				if (begin % 2 == 0){	// zeby ustawic sie na indeksie pierwszej liczby nieparzystej
					i++;
				}
				for (i; i < m; i += 2){
					array[i] = true;
				}

				endd = (long long) sqrt((long double) end);		// iterowac wystarczy do sqrt(end)
				walk = list->next;		// od liczby numer 3 - wiem ze istnieje bo robie to tylko dla granicy > 10000
				i = begin;	// ustawiam na poczatek przedzialu

				while (walk->value <= endd){
					i = begin;
					if (i % 2 == 0){		// ustawiam sie na liczbe nieparzysta
						i++;
					}

					while (i % walk->value != 0){	// pierwsza podzielna przez dana liczbe pierwsza 
						i += 2;
					}

					while (i <= end){		// wykreslanie, istotne jest i - begin zeby otrzymac indeks z tablicy
						array[i - begin] = false;
						i += walk->value;
					}

					walk = walk->next;
					if (walk == NULL){	// gdy skonczy sie lista to przerywam
						break;
					}
				}

				i = begin;
				if (i % 2 == 0){	// liczba nieparzysta
					i++;
				}
				for (i; i <= end; i += 2){	// dopisuje znalezione liczby pierwsze
					if (array[i - begin] == true){
						addElement(i);
					}	
				}			

				begin += m;		// ustawiam sie na kolejnym przedziale o dlugosci m = sqrt(b)
				end += m;

				if (begin >= b){	// koniec algorytmu
					if (array != NULL){
						delete[] array;
					}
					break;
				}
			}
		}

		void addElement(long long value){
			PrimeNumber* nowy = new PrimeNumber(value);

			if (list == NULL){
				list = nowy;
			}
			else{
				last->next = nowy;			
			}

			last = nowy;
		}

		PrimeNumber* mindp(long long number){			// mindp = dla liczb pierwszych malych elem z listy a dla duzych NULL
			PrimeNumber* walk = list;

			while (walk != NULL){
				if (number % walk->value == 0){		// zwracam adres najmniejszego dzielnika pierwszego
					return walk;
				}

				walk = walk->next;
			}

			return NULL;
		}

		void checkConditions(){
			long long w[6];		// tablica do ktorej zliczam wystapienia warunkow
			long long number = wspX;
			long long x;
			PrimeNumber* min;
			for (int i = 0; i < 6; i++){
				w[i] = 0;
			}

			while (number <= wspY){
				if (min = mindp(number), min == NULL || min->value == number){		// liczba pierwsza mala lub duza
					w[0]++;						
				} else if (number <= 5 * min->squareOfValue){	// ograniczenia aby uniknac nadmiernych zapytan
					x = number / min->squareOfValue;
					if (number % min->squareOfValue != 0){		// wybor warunku, gdy nie dzieli sie przez kwadrat lpierwszej
						x++;						// to potrzebuje "sufit" z rezultatu
					}

					w[x]++;	
				}			

				number++;
			}

			cout << w[0] << "," << w[1] << "," << w[2] << "," << w[3] << "," << w[4] << "," << w[5] << endl;
		}

		~Compartment(){
			if (list != NULL){
				PrimeNumber* walk = list;
				PrimeNumber* p;

				while (walk != NULL){
					p = walk;				
					walk = walk->next;
					delete p;							
				}
			}

			last = NULL;
			list = NULL;
		}
};

int main(){
	long long set, b, e;
	Compartment* current = NULL, *p = NULL;

	cin >> set;
	while (set-- > 0){
		cin >> b >> e;
		current = new Compartment(b, e);
		current->checkConditions();

		p = current;
		delete p;
		current = NULL;
		p = NULL;
	}

	return 0;
}