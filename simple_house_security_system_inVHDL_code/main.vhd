library ieee;
use ieee.std_logic_1164.all;

entity sistem is
	port(ma,mb,ga,gb,sup,clock,reset:in std_logic;
	alarma:out std_logic;
	x,y,z,t:integer;
	modul:in std_logic_vector (1 downto 0));
end entity;

architecture structural of sistem is
signal n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n14,n13,n15,n12:std_logic;
signal n11:std_logic_vector (3 downto 0);

component  numarator is
	port(enable:in std_logic;
	clock,reset:in std_logic;
	counter:out std_logic_vector (3 downto 0);
	tc:out std_logic);
end component ;

component  sau is
	port(a,b,c:in std_logic;
	z:out std_logic);
end component ;

 component  si is
	port(a,b,c:in std_logic;
	z:out std_logic);
end component ;


component  acasa is
	port(ma,mb,ga,gb,enable:in std_logic;
	alarma2:out std_logic);
end component ;

component  plecat is
	port(ma,mb,ga,gb,sup,enable:in std_logic;
	alarma3:out std_logic);
end component ;

component  comparator_2 is
	port(a,b:in std_logic_vector (1 downto 0);
	enable:in std_logic;
	egal:out std_logic);
end component ;


component  confirmare_pin is
	port(a,b,c,d:in integer;
	x0,x1,x2,x3:in integer;
	z:out std_logic);
end component ;	

component mux is
	port(a,b,c:in std_logic;
	s:in std_logic_vector (1 downto 0);
	alarma:out std_logic);
end component ;

begin
	c1:confirmare_pin port map(a=>x,b=>y,c=>z,d=>t,x0=>0,x1=>0,x2=>0,x3=>0,z=>n1);
	c2:comparator_2 port map(a=>modul,b=>"00",enable=>n1,egal=>n2);
	c3:si port map(a=>n2,b=>'0',c=>'0',z=>n5);
	c4:comparator_2 port map(a=>modul,b=>"10",enable=>n1,egal=>n6);
	c5:acasa port map(ma=>ma,mb=>mb,ga=>ga,gb=>gb,enable=>n6,alarma2=>n7);
	c6:comparator_2 port map(a=>modul,b=>"11",enable=>n1,egal=>n8);
	c7:numarator port map(enable=>n8,clock=>clock,reset=>reset,counter=>n11,tc=>n9);
	c8:plecat port map(ma=>ma,mb=>mb,ga=>ga,gb=>gb,sup=>sup,enable=>n9,alarma3=>n10);
	--c9:sau port map(a=>n5,b=>n7,c=>n10,z=>alarma);
     c9:mux port map(a=>n5,b=>n7,c=>n10,alarma=>alarma,s=>modul);
end architecture;
