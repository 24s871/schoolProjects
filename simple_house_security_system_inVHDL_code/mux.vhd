library ieee;
use ieee.std_logic_1164.all;

entity mux is
	port(a,b,c:in std_logic;
	s:in std_logic_vector (1 downto 0);
	alarma:out std_logic);
end entity;

architecture cmp of mux is
begin
	process(a,b,c,s)
	begin
		if(s<="00") then
			alarma<=a;
		elsif (s<="10" or s<="01") then
			alarma<=b;
		else alarma<=c;
		end if;
		end process;
		end architecture;
		